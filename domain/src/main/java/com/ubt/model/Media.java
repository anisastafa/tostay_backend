package com.ubt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media", schema = "public")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int media_ID;

    @Column(name = "name")
    private String name;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "pic")
    @Lob
    private byte[] pic;

    public Media(String name, String mimeType, byte[] pic) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
    }
}
