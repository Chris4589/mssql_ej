package com.is4tech.sql.demo.services.dto;

import com.is4tech.sql.demo.models.Channels;

import java.util.List;
import java.util.Optional;

public interface IChannelDTO {
  public Channels createChanel(Channels channel, Long user_id);
  public Optional<Channels> getChannelByID (Long id);
  public List<Channels> getALL();
  public Channels updateChannelById(Long id, Channels channel);
  public void deleteChannelById(Long id);
}
