package me.aaronfulton.rendering.utils;

import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Matrix4f;

public class tranformationUtils {
    public static Matrix4f matrixFromTransform(Transformation transformation) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(transformation.getTranslation());
        matrix.rotate(transformation.getLeftRotation());
        matrix.scale(transformation.getScale());
        matrix.rotate(transformation.getRightRotation());
        return matrix;
    }

    public static void applyTransformationWithInterpolation(Display display, Transformation transformation) {
        if (display.getTransformation().equals(transformation)) return;
        display.setTransformation(transformation);
        display.setInterpolationDelay(0);
    }

    public static void applyTransformationWithInterpolation(Display display, Matrix4f matrix) {
        Transformation oldTransform = display.getTransformation();
        display.setTransformationMatrix(matrix);

        if (oldTransform.equals(display.getTransformation())) return;
        display.setInterpolationDelay(0);
    }

}
