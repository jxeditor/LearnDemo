import jieba
import jieba.posseg as posseg

text = "今天的天气真好"
# jieba.cut得到的是generator形式的结果
seg = jieba.cut(text)  
print(' '.join(seg)) 


# 形如pair('word, 'pos')的结果
seg = posseg.cut(text)  
print([se for se in seg]) 

