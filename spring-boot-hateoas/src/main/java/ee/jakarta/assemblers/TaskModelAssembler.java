package ee.jakarta.assemblers;


import ee.jakarta.controllers.TaskRestController;
import ee.jakarta.controllers.UserRestController;
import ee.jakarta.entities.Task;
import ee.jakarta.models.TaskModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<Task, TaskModel> {
    public TaskModelAssembler() {
        super(TaskRestController.class, TaskModel.class);
    }

    @Override
    public TaskModel toModel(Task task) {
        TaskModel taskModel = instantiateModel(task);
        taskModel.add(linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel());
        taskModel.setTaskId(task.getId());
        taskModel.setTaskName(task.getTaskName());
        return taskModel;
    }

    public CollectionModel<TaskModel> toCollectionModel(Iterable<? extends Task> tasks){
        CollectionModel<TaskModel> taskModels = super.toCollectionModel(tasks);
        taskModels.add(linkTo(methodOn(UserRestController.class).findAll()).withSelfRel());
        return taskModels;
    }

}
