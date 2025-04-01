module uni.usic.taskmanagerv1javafx {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens uni.usic.taskmanager to javafx.fxml;
    exports uni.usic.taskmanager;
}