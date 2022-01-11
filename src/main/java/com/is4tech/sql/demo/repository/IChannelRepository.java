package com.is4tech.sql.demo.repository;

import com.is4tech.sql.demo.models.Channels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChannelRepository extends JpaRepository<Channels, Long> {
}
