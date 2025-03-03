package jpashop.study.service;

import lombok.Getter;

@Getter
public class AlbumDto extends ItemDto{
    private String artist;
    private String etc;

    public void setArtist(String artist) {
        if(this.artist == null  || !this.artist.equals(artist)) this.artist = artist;
    }

    public void setEtc(String etc) {
        if(this.etc == null || !this.etc.equals(etc)) this.etc = etc;
    }
}
