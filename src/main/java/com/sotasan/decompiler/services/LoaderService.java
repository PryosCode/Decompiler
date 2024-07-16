package com.sotasan.decompiler.services;

import com.sotasan.decompiler.controllers.TreeController;
import com.sotasan.decompiler.controllers.TabsController;
import com.sotasan.decompiler.controllers.WindowController;
import com.sotasan.decompiler.models.ArchiveModel;
import com.sotasan.decompiler.models.BaseModel;
import com.sotasan.decompiler.models.FileModel;
import com.sotasan.decompiler.models.PackageModel;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;
import java.util.concurrent.CompletableFuture;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class LoaderService {

    public static void loadAsync(File file) {
        CompletableFuture.runAsync(() -> {

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState((JFrame) WindowController.INSTANCE.getComponent(), Taskbar.State.INDETERMINATE);

            try {

                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();
                ArchiveModel archive = new ArchiveModel(file.getName());

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    BaseModel packageModel = getChildByPath(archive, entry.getName());
                    if (entry.isDirectory())
                        packageModel.getChildren().add(new PackageModel(entry.getName()));
                    else
                        packageModel.getChildren().add(new FileModel(jar, entry));
                }

                WindowController.INSTANCE.activate();
                TabsController.getINSTANCE().clearTabs();
                TreeController.getINSTANCE().setArchive(archive);

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
                Taskbar.getTaskbar().setWindowProgressState((JFrame) WindowController.INSTANCE.getComponent(), Taskbar.State.OFF);

        });
    }

    private static BaseModel getChildByPath(@NotNull BaseModel baseModel, String path) {
        for (BaseModel child : baseModel.getChildren())
            if (child instanceof PackageModel && path.startsWith(child.getPath()))
                return getChildByPath(child, path);
        return baseModel;
    }

}