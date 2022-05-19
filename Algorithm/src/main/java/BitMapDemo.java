/**
 * @Author xz
 * @Date 2022/5/18 14:30
 * @Description TODO
 */
public class BitMapDemo {
    public static void main(String[] args) {
        BitMap bitMap1 = new BitMap(100);
        BitMap bitMap2 = new BitMap(100);
        long size = bitMap1.size;

        // hh=12时
        bitMap1.setBit(1);
        bitMap1.setBit(34);
        bitMap1.setBit(30);
        // hh=13时
        bitMap2.setBit(30);
        bitMap2.setBit(31);
        bitMap2.setBit(34);

        // 计算去重统计值
        int distinct = 0;
        for (int i = 0; i < size; i++) {
            int tmp = bitMap1.getBitsMap()[i] | bitMap2.getBitsMap()[i];
            distinct += countString(Integer.toBinaryString(tmp), "1");
        }
        System.out.println(distinct);
    }

    public static int countString(String str, String s) {
        String str1 = str.replaceAll(s, "");
        int len1 = str.length(), len2 = str1.length(), len3 = s.length();
        return (len1 - len2) / len3;
    }
}
