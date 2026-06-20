import torch
import torch.nn as nn
import torch.optim as optim
import math

# device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

# Tokenization
def tokenize(text,vocab):
    return [vocab.get(word,vocab[""]) for word in text.split()]

# Embedding Layer
class Embedding(nn.Module):
    def __init__(self,vocab_size,embedding_dim):
        super(Embedding,self).__init__()
        self.embedding = nn.Embedding(vocab_size,embedding_dim)

    def forward(self,x):
        return self.embedding(x)

# Positional Encoding
#this adds a unique positional signal to each of these words
class PositionalEncoding(nn.Module):
    def __init__(self,embedding_dim,max_sql_len=5000):
        super(PositionalEncoding,self).__init__()
        pe = torch.zeros(max_sql_len,embedding_dim)
        position = torch.arange(0,max_sql_len,dtype=torch.float).unsqueeze(1)

        #scaling factor for the frequency component
        div_term = torch.exp(torch.arange(0,embedding_dim,2).float() * (-math.log(10000.0)/embedding_dim))



        pe[:,0::2] = torch.sin(position*div_term) #for even indexes
        pe[:,1::2] = torch.cos(position*div_term) #for odd indexes

        pe = pe.unsqueeze(0).transpose(0,1)
        self.register_buffer('pe',pe)

    def forward(self,x):
        return x+self.pe[:x.size(0),:]

# Self-Attention
class SelfAttention(nn.Module):
    def __init__(self,embedding_dim):
        super(SelfAttention,self).__init__()
        self.Query = nn.Linear(embedding_dim,embedding_dim)
        self.Key = nn.Linear(embedding_dim,embedding_dim)
        self.Value = nn.Linear(embedding_dim,embedding_dim)

    def forward(self,x):
        queries = self.Query(x)  #what each word want to focus on
        keys = self.Key(x)       #what each word has to offer
        values = self.Value(x)   #signifies the actual information of each word

        scores = torch.bmm(queries,keys.transpose(1,2))/torch.sqrt(torch.tensor(x.size(-1),dtype=torch.float32))  #dot product -> llogariten attention scores, bmm->batch matrix multiplication


        attention_weights = torch.softmax(scores,dim=-1)  #softmax converts scores into probabilities

        attended_values = torch.bmm(attention_weights,values)

        return attended_values



# Transformer Block
class TransformerBlock(nn.Module):
    def __init__(self,embedding_dim,hidden_dim):
        super(TransformerBlock,self).__init__()
        self.attention = SelfAttention(embedding_dim)
        self.feed_forward = nn.Sequential(
            nn.Linear(embedding_dim,hidden_dim),
            nn.ReLU(),
            nn.Linear(hidden_dim,embedding_dim)
        )
        self.norm1 = nn.LayerNorm(embedding_dim)  #normalization layer
        self.norm2 = nn.LayerNorm(embedding_dim)

    def forward(self,x):
        attended = self.attention(x)
        x = self.norm1(x + attended)
        forwarded = self.feed_forward(x)
        x = self.norm2(x + forwarded)    #Residual connection + LayerNorm
        return x

# Full Language Model
class SimpleLLM(nn.Module):
    def __init__(self,vocab_size,embedding_dim,hidden_dim,num_layers):
        super(SimpleLLM,self).__init__()
        self.embedding = nn.Embedding(vocab_size,embedding_dim)
        self.positional_encoding = PositionalEncoding(embedding_dim)
        self.transformer_blocks = nn.Sequential(*[TransformerBlock(embedding_dim,hidden_dim)])
        self.output = nn.Linear(embedding_dim,vocab_size)

    def forward(self,x):
        x = self.embedding(x)
        x = x.transpose(0,1)
        x = self.positional_encoding(x)
        x = x.transpose(0,1)
        x = self.transformer_blocks(x)
        x = self.output(x)
        return x

# Training the model
vocab = {
    "hello": 0, "world": 1, "how": 2, "are": 3, "you": 4,
    "": 5, "good": 6, "morning": 7, "evening": 8, "night": 9,
    "friend": 10, "nice": 11, "to": 12, "meet": 13, "learning": 14,
    "AI": 15, "is": 16, "fine": 17, "great": 18, "awesome": 19,
    "day": 20, "doing": 21, "today": 22, "hope": 23, "all": 24,
    "well": 25
}

vocab_size = len(vocab)
embedding_dim = 16
hidden_dim = 32
num_layers = 2

model = SimpleLLM(vocab_size,embedding_dim,hidden_dim,num_layers)
criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(model.parameters(), lr=0.001)

data = [
    "hello world how are you",
    "how are you hello world",
    "good morning friend",
    "nice to meet you",
    "learning AI is fun",
    "have a great day",
    "hope you are doing well",
    "AI is awesome",
    "what are you doing today",
    "good evening to all"
]

tokenized_data = [tokenize(sentence, vocab) for sentence in data] 
loss = 0

for epoch in range(100):
    for sentence in tokenized_data:
        # print(f"Sentence from tokenized_data : {sentence}")
        for i in range(1,len(sentence)):
            input_seq = torch.tensor(sentence[:i]).unsqueeze(0)
            target = torch.tensor(sentence[i]).unsqueeze(0)
            optimizer.zero_grad()
            output = model(input_seq)
            loss = criterion(output[:,-1,:], target)
            loss.backward()
            optimizer.step()

    if epoch % 10 == 0 :
        print(f"Epoch : {epoch}, Loss : {loss.item()}")

# Using the model
input_text = "good"
input_tokens = tokenize(input_text,vocab)
input_tensor = torch.tensor(input_tokens).unsqueeze(0)
output = model(input_tensor)
predicted_token = torch.argmax(output[:, -1, :]).item()
print(f"Input: {input_text}, Predicted: {list(vocab.keys())[list(vocab.values()).index(predicted_token)]}")





