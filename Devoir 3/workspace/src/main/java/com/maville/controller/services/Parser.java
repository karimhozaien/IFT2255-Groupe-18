package com.maville.controller.services;

import java.util.ArrayList;
import java.util.List;
import com.maville.controller.repository.WorkRepository.Result.Record;
import com.maville.model.Project;

/**
 * Classe utilitaire pour analyser et convertir des enregistrements récupérés depuis le dépôt
 * en objets utilisables tels que {@code Project} ou d'autres formats selon les besoins.
 *
 * @param <T> Le type d'objet à générer à partir des enregistrements.
 */
public class Parser<T> {
    List<Record> records;

    public Parser(List<Record> records) {
        this.records = records;
    }

    /**
     * Analyse les enregistrements et retourne une liste d'objets du type spécifié.
     *
     * @param option Le type d'analyse à effectuer (par exemple, "works" pour des projets ou
     *               "road_obstructions" pour des informations sur les obstructions).
     * @param type La classe du type d'objet à générer.
     * @return Une liste d'objets du type spécifié, construite à partir des enregistrements.
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
                String output = record.getStreetImpactType() + " " + record.getStreetId()
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
