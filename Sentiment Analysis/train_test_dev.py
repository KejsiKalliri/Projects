import pandas as pd
from sklearn.model_selection import train_test_split

file_path = "sentiment_analysis_dataset"
data = pd.read_csv(file_path)

data.dropna(inplace=True)

X_train, X_temp, y_train, y_temp = train_test_split(data, data['label'], test_size=0.2, random_state=42)

X_dev, X_test, y_dev, y_test = train_test_split(X_temp, y_temp, test_size=0.5, random_state=42)

train_data = pd.concat([X_train, y_train], axis=1)
test_data = pd.concat([X_test, y_test], axis=1)
dev_data = pd.concat([X_dev, y_dev], axis=1)

train_data.to_csv('train_data.csv', index=False)
test_data.to_csv('test_data.csv', index=False)
dev_data.to_csv('dev_data.csv', index=False)

