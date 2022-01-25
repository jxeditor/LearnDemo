from nltk import word_tokenize
from nltk import TextCollection

sents=['i like jike','i want to eat apple','i like lady gaga']
# 首先进行分词
sents=[word_tokenize(sent) for sent in sents]

# 构建语料库
corpus=TextCollection(sents)

# 计算TF
tf=corpus.tf('one',corpus)

# 计算IDF
idf=corpus.idf('one')

# 计算任意一个单词的TF-IDF
tf_idf=corpus.tf_idf('one',corpus)

print(tf_idf)