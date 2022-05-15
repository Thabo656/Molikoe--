package com.example.molikoe;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class HelloController {

    private String path;
    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider ProgressBar;
    @FXML
    private Slider Volume;


    public void ChooseMethod(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        //CHECK IF PATH CORRECT
        if(path != null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();

            //FIT VIDEO TO THE SCEEN
            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    ProgressBar.setValue(newValue.toSeconds());

                }
            });

            ProgressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(ProgressBar.getValue()));
                }
            });
            ProgressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(ProgressBar.getValue()));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    ProgressBar.setMax(total.toSeconds());
                }
            });

            Volume.setValue(mediaPlayer.getVolume() * 100);
            Volume.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(Volume.getValue()/100);
                }
            });

            mediaPlayer.play();
        }


    }
    public void play(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    public void Pause(ActionEvent event){
        mediaPlayer.pause();
    }
    public void Stop(ActionEvent event){
        mediaPlayer.stop();
    }
    public void SlowRate(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    public void FastForward(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    public void Back10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-10)));
    }
    public void Skip10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
    }




}