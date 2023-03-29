package com.util;

public class HtmlSplit {

    public static void main(String[] args) {
        String html = "<p></p><p><font style=\"vertical-align: inherit;\"><font style=\"vertical-align: inherit;\">小号sssssssssssssssssssss</font></font></p>";
        String htmlSplit = getHtmlSplit(html);
        System.out.println(htmlSplit);
    }
    public static String getHtmlSplit(String html){
        html=html.replaceAll("<a href[^>]*>", "");
        html=html.replaceAll("</a>", "");
        html=html.replaceAll("<img[^>]*>", "");
        html=html.replaceAll("<b[^>]*>", "");
        html=html.replaceAll("</b>", "");
        html=html.replaceAll("<p[^>]*>", "");
        html=html.replaceAll("</p>", "");
        html=html.replaceAll("<br[^>]*>", "");
        html=html.replaceAll("<div[^>]*>", "");
        html=html.replaceAll("</div>", "");
        html=html.replaceAll("<iframe src[^>]*>", "");
        html=html.replaceAll("</iframe>", "");
        html=html.replaceAll("<span style[^>]*>", "");
        html=html.replaceAll("</span>", "");
        html=html.replaceAll("<h1[^>]*>", "");
        html=html.replaceAll("</h1>", "");
        html=html.replaceAll("<h2[^>]*>", "");
        html=html.replaceAll("</h2>", "");
        html=html.replaceAll("<h3[^>]*>", "");
        html=html.replaceAll("</h3>", "");
        html=html.replaceAll("<h4[^>]*>", "");
        html=html.replaceAll("</h4>", "");
        html=html.replaceAll("<h5[^>]*>", "");
        html=html.replaceAll("</h5>", "");
        html=html.replaceAll("<font style[^>]*>", "");
        html=html.replaceAll("</font>", "");
        html=html.replaceAll("&nbsp", "");
        return html;
    }
}