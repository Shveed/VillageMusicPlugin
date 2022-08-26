package ru.shveed.vmp;

import ru.shveed.vmp.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicThread implements Runnable {

    private List<Song> loadedSongsList;

    public MusicThread() {
        loadedSongsList = new ArrayList<>();
    }

    @Override
    public void run() {

        Song currentSong = loadedSongsList.get(0);

        /* todo:
         Проверяем, играет ли какя-то музыка

         Включаем следующую

         В цикле всех игроков добавляем в плеер

         врубаем плеер
         */

        NotificationManager.printCurrentSongPlaying(currentSong.getAuthor(), currentSong.getName());
    }
}
