package com.maville.controller.services;

import java.util.ArrayList;
import java.util.List;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;

public class Parser<T> {
    List<Record> records;

    public Parser(List<Record> records) {
        this.records = records;
    }

    /**
     * Analyse les {@code records} pour créer une liste de projets.
     *
     * @return Une liste de {@code Project} construite à partir des informations contenues dans chaque {@code Record}.
     */
    public List<T> initializeParsing(String option, Class<T> type) {
        List<T> items = new ArrayList<>();

        if (type.equals(Project.class) && option.equals("works")) {
            for (Record record : records) {
                Project project = new Project(record.getId(),
                        createTitle(record),
                        null,
                        parseTypeOfWork(record),
                        record.getAffectedNeighbourhood(),
                        record.getAffectedStreets(),
                        parseDate(record.getStartDate()),
                        parseDate(record.getEndDate()),
                        parseWorkSchedule(record),
                        Project.WorkStatus.ONGOING
                );
                items.add(type.cast(project));
            }
        } else if (type.equals(String.class) && option.equals("road_obstructions")) {
            for (Record record : records) {
                String output = record.getId() + ". " + record.getStreetImpactType() + " " + record.getStreetId()
                        + "sur " + record.getStreetImpactWidth();
                items.add(type.cast(output));
            }
        }

        return items;
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

    private String parseWorkSchedule(Record record) {
        return String.join(",", record.buildScheduleList());
    }
}
