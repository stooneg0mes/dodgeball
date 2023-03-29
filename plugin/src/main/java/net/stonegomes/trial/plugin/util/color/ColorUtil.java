package net.stonegomes.trial.plugin.util.color;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorUtil {

    private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public static List<String> translate(List<String> strings) {
        return strings.stream()
            .map(ColorUtil::translate)
            .collect(Collectors.toList());
    }

    public static String translate(String startTag, String endTag, String string) {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);

        Matcher matcher = hexPattern.matcher(string);
        StringBuffer buffer = new StringBuffer(string.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static String translate(String string) {
        return translate("<#", ">", string);
    }

}