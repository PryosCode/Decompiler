package com.hohltier.decompiler.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@UtilityClass
public class ResourceUtil {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/language", Locale.getDefault());

    public static Image getLogo() {
        return Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(ResourceUtil.class.getClassLoader().getResource("logo/logo.png")));
    }

    // TODO
    public static String getVersion() {
        return "";
    }

    public static @NotNull String getTranslation(String key) {
        return resourceBundle.getString(key);
    }

}