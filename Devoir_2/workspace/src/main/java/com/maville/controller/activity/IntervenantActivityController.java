package com.maville.controller.activity;

import com.maville.controller.repository.WorkRepository;
import com.maville.model.WorkRequestForm;
import com.maville.view.MenuView;

import java.util.List;

public class IntervenantActivityController {
    public boolean submitProject() {
        // TODO
        return false; // replace
    }

    public boolean updateProject() {
        // TODO
        return false; // replace
    }

    public void consultWorkRequests() {
        // TODO
        WorkRepository workRepository = new WorkRepository();
        List<WorkRequestForm> workRequests= workRepository.fetchWorkRequests();
        MenuView.showResults(workRequests);
    }
}
