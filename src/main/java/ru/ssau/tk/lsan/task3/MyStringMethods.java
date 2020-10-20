package ru.ssau.tk.lsan.task3;

public interface MyStringMethods {
    void printMyString(String str);

    void getMyBytes(String str);

    void myVoid();

    boolean isPalindromic(String str);

    boolean isEqualStr(String str1, String str2);

    void symbolStrTest();

    int findSubStringIndex(String str1, String str2);

    int findSubStringIndexInHalf(String str1, String str2);

    int findSubStringIndexInRightHalf(String str1, String str2);

    int stringWithPrefixAndPostFix(String prefix, String[] str, String postfix);

    int stringWithPrefixAndPostFixWithoutSpaces(String prefix, String[] str, String postfix);

    String replaceThatStringWith(String thatString, String whatToReplace, String replacement);

    String stringFromTo(String str, int from, int to);
}
