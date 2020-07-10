package com.leandoer.entity.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Manufacturer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManufacturerDto {
    private long id;
    private String name;

    public ManufacturerDto(Manufacturer manufacturer) {
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
    }

    public Manufacturer toManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(this.id);
        manufacturer.setName(this.name);
        return manufacturer;
    }
}
