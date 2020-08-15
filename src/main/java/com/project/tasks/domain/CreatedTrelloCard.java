package com.project.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.domain.Badges;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCard {

    @JsonProperty("id")
    private String id;

//    @JsonProperty("name")
//    private String name;

//    @JsonProperty("shortUrl")
//    private String shortUrl;

    @JsonProperty("badges")
    private Badges badges;

}
