package com.maville.controller.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;

public class Parser {
    List<Record> records;

    public Parser(List<Record> records) {
        this.records = records;
    }

    public List<Project> initializeParsing() {
        List<Project> projects = new ArrayList<>();

        for (Record record : records) {
            Project project = new Project(record.getId(),
                    record.getTitle(),
                    parseTypeOfWork(record),
                    record.getAffectedNeighbourhood(),
                    record.getAffectedStreets(),
                    parseDate(record.getStartDate()),
                    parseDate(record.getEndDate()),
                    parseWorkSchedule(record)
            );

            System.out.println(project);
            projects.add(project);
        }

        return projects;
    }

    private String parseDate(String date) {
        return date.split("T")[0];
    }

    private Project.TypeOfWork parseTypeOfWork(Record record) {
        return record.getTypeOfWork() ;
    }

    private List<String> parseWorkSchedule(Record record) {
        return record.buildScheduleList();
    }
}
