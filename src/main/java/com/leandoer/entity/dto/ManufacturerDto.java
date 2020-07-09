package com.leandoer.entity.dto;


import com.leandoer.entity.Manufacturer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManufacturerDto {
    private long id;
    private String name;

    public ManufacturerDto(Manufacturer manufacturer) {
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
    }

    public Manufacturer toManufcaturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(this.id);
        manufacturer.setName(this.name);
        return manufacturer;
    }
}
