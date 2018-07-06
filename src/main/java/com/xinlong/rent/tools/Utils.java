package com.xinlong.rent.tools;

import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;
public class Utils {

    /*public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }*/

    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {


        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {
//      String[] ss = getUUID(10);

            System.out.println("ss[]=====" + convertMD5("111111"));

    }





    public static String convertMD5(String inStr){
            String re_md5 = new String();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(inStr.getBytes());
                byte b[] = md.digest();

                int i;

                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }

                re_md5 = buf.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return re_md5;
    }

}
