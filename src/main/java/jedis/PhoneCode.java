package jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author: baifengxiao
 * @date: 2022/4/6 13:14
 */
public class PhoneCode {
    public static void main(String[] args) {
        String code = getCode();
        //模拟验证码发送
        verifyCode("18111111856");
//        getRedisCode("18111111856","292963");
    }

    //验证码校验
    public static void getRedisCode(String phone, String code) {

        Jedis jedis = new Jedis("192.168.44.101", 6379);
        //获取验证码
        String codetKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codetKey);
        //判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }

    //每个手机每天发送3次验证码
    public static void verifyCode(String phone) {
        Jedis jedis = new Jedis("192.168.44.101", 6379);

        //发送次数key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codetKey = "VerifyCode" + phone + ":code";

        String count = jedis.get(countKey);
        if (count == null) {
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            System.out.println("发送3次，不能再发送了");
            jedis.close();
            return;
        }
        
        //    把验证码放到redis里面
        String vcode = getCode();
        jedis.setex(codetKey, 120, vcode);
        System.out.println(vcode);
        jedis.close();
    }


    //1,生成6位验证码
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}
