package utils;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class TextUtils {
    public static SpannableStringBuilder makeSectionOfTextBold(String text, String... textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem : textToBold)
            if (textItem.length() > 0 && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase();
                String testTextToBold = textItem.toLowerCase();
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0)
                    builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
            }

        return builder;
    }
}
