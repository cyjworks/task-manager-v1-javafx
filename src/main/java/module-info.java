module uni.usic.taskmanagerv1javafx {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens uni.usic.taskmanager to javafx.fxml;
    exports uni.usic.taskmanager;
    exports uni.usic.taskmanager.views.tasks;
    opens uni.usic.taskmanager.views.tasks to javafx.fxml;
    exports uni.usic.taskmanager.controller.tasks;
    opens uni.usic.taskmanager.controller.tasks to javafx.fxml;
}