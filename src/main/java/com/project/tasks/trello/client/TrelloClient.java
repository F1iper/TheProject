package com.project.tasks.trello.client;

import static java.util.Optional.ofNullable;

import com.project.tasks.domain.CreatedTrelloCardDto;
import com.project.tasks.domain.TrelloBoardDto;
import com.project.tasks.domain.TrelloCardDto;
import com.project.tasks.trello.config.TrelloConfig;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrelloClient {

  private final RestTemplate restTemplate;
  private final TrelloConfig trelloConfig;

  private URI getUrl() {
    return UriComponentsBuilder.fromHttpUrl(
        trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getUsername() + "/boards")
        .queryParam("key", trelloConfig.getTrelloAppKey())
        .queryParam("token", trelloConfig.getTrelloToken())
        .queryParam("fields", "name,id")
        .queryParam("lists", "all").build().encode().toUri();
  }

  public List<TrelloBoardDto> getTrelloBoards() {
    try {
      log.debug(getUrl().toString());
      TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getUrl(), TrelloBoardDto[].class);
      return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
    } catch (RestClientException e) {
      log.error(e.getMessage(), e);
      return new ArrayList<>();
    }

  }

  public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
    URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
        .queryParam("key", trelloConfig.getTrelloAppKey())
        .queryParam("token", trelloConfig.getTrelloToken())
        .queryParam("name", trelloCardDto.getName())
        .queryParam("desc", trelloCardDto.getDescription())
        .queryParam("pos", trelloCardDto.getPos())
        .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();
    log.debug(url.toString());
    return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
  }
}
