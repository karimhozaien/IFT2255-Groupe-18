package com.maville.controller.repository;

import java.util.ArrayList;
import java.util.List;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;

public class Parser {
    List<Record> records;

    public Parser(List<Record> records) {
        this.records = records;
    }

    /**
     * Analyse les {@code records} pour créer une liste de projets.
     *
     * @return Une liste de {@code Project} construite à partir des informations contenues dans chaque {@code Record}.
     */
    public List<Project> initializeParsing() {
        List<Project> projects = new ArrayList<>();

        for (Record record : records) {
            Project project = new Project(record.getId(),
                    createTitle(record),
                    parseTypeOfWork(record),
                    record.getAffectedNeighbourhood(),
                    record.getAffectedStreets(),
                    parseDate(record.getStartDate()),
                    parseDate(record.getEndDate()),
                    parseWorkSchedule(record),
                    Project.WorkStatus.ONGOING
            );
            projects.add(project);
        }
        return projects;
    }

    private String createTitle(Record record) {
        String typeOfWork = record.getTypeOfWorkRaw();
        if (typeOfWork.trim().equals("Autre")) {
            typeOfWork = "Travaux";
        }
        return typeOfWork + " direction " + record.getAffectedStreets();
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
