ALTER TABLE StudentGroups ADD COLUMN course INTEGER REFERENCES Courses(_id);
CREATE INDEX index_StudentGroups ON StudentGroups(_id, course);