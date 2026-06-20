import requests

url = "https://raw.githubusercontent.com/dD2405/Twitter_Sentiment_Analysis/master/train.csv"
file_path = "sentiment_analysis_dataset"

response = requests.get(url)

with open(file_path, 'wb') as f:
    f.write(response.content)
