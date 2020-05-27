package be.pxl.student.dao;

import be.pxl.student.entity.Label;

public interface LabelDao {
    Label findLabelById(long labelId);
    Label findLabelByName(String name);
    void removeLabel(Label label);
    void saveLabel(Label label);
    // TODO more methods?
}
