package arrow.framework.util;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class StringUtilsTest {


    @Test
    public void testReplaceSpecialCharacter() throws Exception {
        // + - = && || > < ! ( ) { } [ ] ^ " ~ * ? : \ /
        String expectedString = "\\!\\(\\)\\{\\}\\[\\]\\^\\*\\?\\:\\/\\\\";
        String actualString = StringUtils.replaceSpecialCharacter("!(){}[]^*?:/\\");
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void testRemoveUrl() {
        String a = "http://www.+qweqweqwe";
        String b = "https://!@#$%^&*()";
        String c = "http://*qweqweqwe";
        String d = "www.{}|11111";
        String e = "WwW.+!_@)(#$";
        Assert.assertEquals("+qweqweqwe", StringUtils.removeUrlToken(a));
        Assert.assertEquals("!@#$%^&*()", StringUtils.removeUrlToken(b));
        Assert.assertEquals("*qweqweqwe", StringUtils.removeUrlToken(c));
        Assert.assertEquals("{}|11111", StringUtils.removeUrlToken(d));
        Assert.assertEquals("+!_@)(#$", StringUtils.removeUrlToken(e));
        Assertions.assertThat(StringUtils.removeUrlToken(null)).isEqualTo(null);
        Assertions.assertThat(StringUtils.removeUrlToken("")).isEqualTo("");
        Assertions.assertThat(StringUtils.removeUrlToken("http://www2.odn.ne.jp/")).isEqualTo("odn.ne.jp/");
        Assertions.assertThat(StringUtils.removeUrlToken("http://www-ng.odn.ne.jp/")).isEqualTo("odn.ne.jp/");
        Assertions.assertThat(StringUtils.removeUrlToken("http://blog.arrow-tech.vn/"))
            .isEqualTo("blog.arrow-tech.vn/");


    }

    @Test
    public void testConvertToHalfWidth() throws Exception {
        Assertions.assertThat(StringUtils.convertToHalfWidth("コンニチハ")).isEqualTo("ｺﾝﾆﾁﾊ");

        Assertions.assertThat(StringUtils.convertToHalfWidth("ＫＤＤＩテクニカルサービスエンジニアリング"))
            .isEqualTo("KDDIﾃｸﾆｶﾙｻｰﾋﾞｽｴﾝｼﾞﾆｱﾘﾝｸﾞ");
    }

    @Test
    public void testConvertToKatakana() throws Exception {
        Assertions.assertThat(StringUtils.convertToHalfWidthKata("浅海電気")).isEqualTo("ｷｱﾝﾊｲﾃﾞｨｱﾝｷ");
        Assertions.assertThat(StringUtils.convertToHalfWidthKata("全角ひらがな")).isEqualTo("ｸｱﾝｼﾞｱｵﾋﾗｶﾞﾅ");
        Assertions.assertThat(StringUtils.convertToHalfWidthKata("半角ｶﾀｶﾅ")).isEqualTo("ﾊﾞﾝｼﾞｱｵｶﾀｶﾅ");
        Assertions.assertThat(StringUtils.convertToHalfWidthKata("全角カタカナ")).isEqualTo("ｸｱﾝｼﾞｱｵｶﾀｶﾅ");
        Assertions.assertThat(StringUtils.convertToHalfWidthKata("ＫＤＤＩテクニカルサービスエンジニアリング"))
            .isEqualTo("ｸｯﾃﾞｨﾃｸﾆｶﾙｻｰﾋﾞｽｴﾝｼﾞﾆｱﾘﾝｸﾞ");

        Assertions.assertThat(StringUtils.convertToHalfWidthKata("KDDIテクニカルサービスエンジニアリング"))
            .isEqualTo("ｸｯﾃﾞｨﾃｸﾆｶﾙｻｰﾋﾞｽｴﾝｼﾞﾆｱﾘﾝｸﾞ");
    }

    @Test
    public void testExtractDomain() throws Exception {
        Assertions.assertThat(StringUtils.extractDomain("209.124.61.162")).isEqualTo("209.124.61.162");
        Assertions.assertThat(StringUtils.extractDomain("blog.arrow-tech.vn")).isEqualTo("blog.arrow-tech");

        Assertions.assertThat(StringUtils.extractDomain("blog.arrow-tech.vn")).isEqualTo("blog.arrow-tech");
        Assertions.assertThat(StringUtils.extractDomainExt("blog.arrow-tech.vn")).isEqualTo("vn");
        Assertions.assertThat(StringUtils.extractDomain("http://blog.arrow-tech.vn")).isEqualTo("blog.arrow-tech");
        Assertions.assertThat(StringUtils.extractDomainExt("http://blog.arrow-tech.vn")).isEqualTo("vn");
        Assertions.assertThat(StringUtils.extractDomain("http://www.blog.arrow-tech.vn")).isEqualTo("blog.arrow-tech");
        Assertions.assertThat(StringUtils.extractDomainExt("http://www.blog.arrow-tech.vn")).isEqualTo("vn");

        Assertions.assertThat(StringUtils.extractDomain("http://arrow-tech.vn")).isEqualTo("arrow-tech");
        Assertions.assertThat(StringUtils.extractDomainExt("http://arrow-tech.vn")).isEqualTo("vn");


        Assertions.assertThat(StringUtils.extractDomain("http://www.kddi.com/corporate/index.html")).isEqualTo("kddi");
        Assertions.assertThat(StringUtils.extractDomainExt("http://www.kddi.com/corporate/index.html"))
            .isEqualTo("com");
        Assertions.assertThat(StringUtils.extractDomain("http://www.nsk-net.co.jp/company/profile.html"))
            .isEqualTo("nsk-net");
        Assertions.assertThat(StringUtils.extractDomainExt("http://www.nsk-net.co.jp/company/profile.html"))
            .isEqualTo("co.jp");
    }
}
