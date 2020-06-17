package be.pxl.student.dao;

import be.pxl.student.entity.Label;

import java.util.List;

public interface LabelDao {
    List<Label> findAllLabels();
    Label findLabelById(long labelId);
    Label findLabelByName(String name);
    void removeLabel(Label label);
    void saveLabel(Label label);
}
