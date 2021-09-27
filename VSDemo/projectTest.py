import requests
from selenium import webdriver

import sys


if __name__ == '__main__':
 # r = requests.get("https://show.1688.com/shili/factory/shop.html?spm=a260k.24006001.krq2n2st.5.7de74d84rzcj5d&__pageId__=99183&cms_id=99183&facMemId=xgm528&topOfferIds=636373170918,643768972954,636702083565,636703023452")#获取网页源代码
 # print(r.text)

 driver = webdriver.Chrome()

 driver.maximize_window()

 driver.get('https://show.1688.com/shili/factory/shop.html?spm=a260k.24006001.krq2n2st.5.7de74d84rzcj5d&__pageId__=99183&cms_id=99183&facMemId=xgm528&topOfferIds=636373170918,643768972954,636702083565,636703023452')
 # print (driver.page_source)

 def run():
    # 获取公司名字
    company = driver.find_element_by_class_name('titleBar').text
    print("公司名字: " + company)
    # 获取公司年龄以及主营信息
    context = driver.find_element_by_class_name('facInfo').text
    print("公司年龄: " + context)

    # 获取公司地址
    address = driver.find_element_by_class_name('location').text
    print("公司地址: " + address)

    # 获取工厂牌级图片
    nameType = driver.find_element_by_class_name('rowItem').find_element_by_tag_name("img").get_attribute("src")
    print("获取工厂牌级图片: " + nameType)

    # 获取定制合约交易
    rightPart = driver.find_elements_by_class_name("rowItem")[1]
    print("获取定制合约交易: " + rightPart.text.replace('\n', '').replace('\r', ''))




run()