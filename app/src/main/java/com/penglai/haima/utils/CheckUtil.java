package com.penglai.haima.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：flyjiang
 * 说明: 检测工具类,包含字符串，手机号，邮箱等检测
 */
public class CheckUtil {
    /**
     * 判断字符串是否为空
     *
     * @param chars 需要判断的字符串
     * @return 为空返回true，不为空返回false
     */
    public static boolean isEmpty(CharSequence chars) {
        return TextUtils.isEmpty(chars);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 需要判断的字符串
     * @return 是数字返回true，不是数字返回false
     */
    public static Boolean isNumber(String str) {
        String expr = "^[0-9]+$";
        return !isEmpty(str) && str.matches(expr) ? true : false;
    }

    /**
     * 判断是否为手机号(截止2015年5月1日,如果有新号段推出，需要增加判断)
     *
     * @param mobiles 需要判断的字符串
     * @return 是手机号返回true，不是手机号返回false
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        2015年6月1日11:02:04
        移动：134、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
        联通：130、131、132、145、155、156、176、185、186
        电信：133、153、177、180、181、189、
        虚拟运营商：170
        总结起来就是第一位必定为1，第二位必定为3或4或5或7或8，其他位置的可以为0-9
        */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 判断是否为手机号比isMobileNO方法更为准确(2015年6月1日)
     *
     * @param inputText 输入的手机号
     * @return 是手机号返回true，不是手机号返回false
     */
    public static boolean isPhoneNO(String inputText) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[57])|(15[^4,\\D])|(17[0678])|(18[0-9]))\\d{8}$");//15[^4,\D]除154以外的数字
        return !isEmpty(inputText) && p.matcher(inputText).matches();
    }

    /**
     * 判断格式是否为email
     *
     * @param email 输入的邮箱
     * @return 正确邮箱返回true, 不正确邮箱返回false
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return !isEmpty(email) && Pattern.compile(str).matcher(email).matches();
    }

    /**
     * 检测字符串是否有emoji表情
     *
     * @param source 需要检测的字符串
     * @return 有emoji表情返回true，没有emoji表情返回false
     */
    public static boolean isContainsEmoji(String source) {
        if (isEmpty(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return 是表情返回true，不是返回表情false
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断用户名是否合法
     *
     * @param name 需要判断的用户名
     * @return 合法返回true, 不合法返回false
     */
    public static boolean isNormalName(String name) {
        /*
        ^  与字符串开始的地方匹配
        (?!_)　　不能以_开头
        (?!.*?_$)　　不能以_结尾
        [a-zA-Z0-9_\u4e00-\u9fa5]+　　至少一个汉字、数字、字母、下划线
        $　　与字符串结束的地方匹配
         */
        String nameRegex = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        //只包含中英文数字和下划线，并且下划线不在首和尾
        return !isEmpty(name) && name.matches(nameRegex);
    }

    /**
     * 判断密码是否合法
     *
     * @param password 需要判断的密码
     * @return 符合返回true，否则返回false
     */
    public static boolean isNormalPassword(String password) {
        String passwordRegex = "^[a-zA-Z0-9_@]+$";
        //只包含中英文数字和下划线以及@
        return !isEmpty(password) && password.matches(passwordRegex);
    }
    /**
     * 限制edittext 不能输入中文
     * @param editText
     */
    public static void setEdNoChinaese(final EditText editText){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = s.toString();
                //注意返回值是char数组
                char[] stringArr = txt.toCharArray();
                for (int i = 0; i < stringArr.length; i++) {
                    //转化为string
                    String value = new String(String.valueOf(stringArr[i]));
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(value);
                    if (m.matches()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                        return;
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editText.addTextChangedListener(textWatcher);
    }
    /**
     * 限制输入两位小数
     * @param editText
     */
    public static void checkFloatNum(final EditText editText){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                //保留两位小数
                String temp = editable.toString().trim();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    editable.delete(posDot + 3, posDot + 4);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }
}
