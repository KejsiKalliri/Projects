import numpy as np
import pandas as pd
import re
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from sklearn.model_selection import GridSearchCV
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import cohen_kappa_score, mean_absolute_error, mean_squared_error


# Ngarkohet dataset-i nga file-et lokale
train_data = pd.read_csv('train_data.csv')
test_data = pd.read_csv('test_data.csv')
dev_data = pd.read_csv('dev_data.csv')


# Heqim rreshtat qe kane vlera te humbura (NaN)
train_data.dropna(inplace=True)
test_data.dropna(inplace=True)
dev_data.dropna(inplace=True)


# Pastrim i tekstit
def clean_text(text):
    text = re.sub(r"http\S+", "", text)            # Heqim URL-
    text = re.sub(r"@\w+", "", text)               # Heqim emrat e perdoruesve
    text = re.sub(r"#\w+", "", text)               # Heqim hashtag-et
    text = re.sub(r"[^a-zA-Z\s]", "", text)        # Heqim shenjat e pikesimit dhe numrat
    text = text.lower()                                        # E kthejme ne shkronja te vogla
    text = re.sub(r"\s+", " ", text).strip()       # Heqim hapesirat e teperta
    return text

train_data['tweet'] = train_data['tweet'].apply(clean_text)
test_data['tweet'] = test_data['tweet'].apply(clean_text)
dev_data['tweet'] = dev_data['tweet'].apply(clean_text)


# Tokenizimi dhe heqja e stopwords
stop_words = set(stopwords.words('english'))

def preprocess_text(text):
    tokens = word_tokenize(text)
    tokens = [word for word in tokens if word not in stop_words]
    return tokens

train_data['tokens'] = train_data['tweet'].apply(preprocess_text)
test_data['tokens'] = test_data['tweet'].apply(preprocess_text)
dev_data['tokens'] = dev_data['tweet'].apply(preprocess_text)


# Lematizimi
lemmatizer = WordNetLemmatizer()

def lemmatize_tokens(tokens):
    return [lemmatizer.lemmatize(word) for word in tokens]

train_data['lemmatized_tokens'] = train_data['tokens'].apply(lemmatize_tokens)
test_data['lemmatized_tokens'] = test_data['tokens'].apply(lemmatize_tokens)
dev_data['lemmatized_tokens'] = dev_data['tokens'].apply(lemmatize_tokens)


# Bashkojme tokens-at e lematizuar
train_data['processed_text'] = train_data['lemmatized_tokens'].apply(' '.join)
test_data['processed_text'] = test_data['lemmatized_tokens'].apply(' '.join)
dev_data['processed_text'] = dev_data['lemmatized_tokens'].apply(' '.join)


# Vektorizojme tekstin duke perdorur TF-IDF
vectorizer = TfidfVectorizer(max_features=5000)
X_train_vec = vectorizer.fit_transform(train_data['processed_text'])
X_test_vec = vectorizer.transform(test_data['processed_text'])
X_dev_vec = vectorizer.transform(dev_data['processed_text'])

y_train = train_data['label']
y_test = test_data['label']
y_dev = dev_data['label']

# Percaktojme modelet (klasifikuesit)
classifiers = {
    "LR": LogisticRegression(max_iter=1000),
    "XRT": ExtraTreesClassifier(n_jobs=-1),
    "MLP": MLPClassifier(hidden_layer_sizes=(40), max_iter=250, activation='relu', solver='adam'),
}

# Percaktojme disa hiperparametra per te bere optimizim
param_grid = {
    'LR': {'C': [0.1, 1, 10]},
    'XRT': {'n_estimators': [100, 200], 'max_depth': [10, 50]},
    'MLP': {'hidden_layer_sizes': [(40,), (50,)]},
}


# Funksion per llogaritjen e metrikave
def calculate_metrics(y_true, y_pred):
    metrics = {}
    metrics['Correctly Classified Instances'] = (y_true == y_pred).sum()
    metrics['Incorrectly Classified Instances'] = (y_true != y_pred).sum()
    metrics['Kappa Statistic'] = cohen_kappa_score(y_true, y_pred)
    metrics['Mean Absolute Error'] = mean_absolute_error(y_true, y_pred)
    metrics['Root Mean Squared Error'] = np.sqrt(mean_squared_error(y_true, y_pred))
    metrics['Relative Absolute Error'] = mean_absolute_error(y_true, y_pred) / np.mean(np.abs(y_true - np.mean(y_true)))
    metrics['Root Relative Squared Error'] = np.sqrt(mean_squared_error(y_true, y_pred)) / np.std(y_true)
    return metrics


# Optimizojme dhe trajnojme modelet
for name, clf in classifiers.items():
    print(f"\nDuke u optimizuar dhe trajnuar modeli {name}...")

    # Optimizimi me GridSearchCV
    grid_search = GridSearchCV(clf, param_grid[name], cv=3)
    grid_search.fit(X_dev_vec, y_dev)
    best_model = grid_search.best_estimator_

    print(f"Parametrat me te mire per modelin {name}: {grid_search.best_params_}")

    # Trajnimi
    best_model = clf.fit(X_train_vec, y_train)

    # Parashikimi
    y_pred = best_model.predict(X_test_vec)

    # Llogartja e metrikave per dataset-in test
    print(f"\nRezultatet per modelin {name} (Test Dataset):")
    metrics = calculate_metrics(y_test, y_pred)
    for metric_name, metric_value in metrics.items():
        print(f"{metric_name}: {metric_value:.4f}")



