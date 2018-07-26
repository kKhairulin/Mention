package com.mention.service;

import com.mention.dao.FavoriteDao;
import com.mention.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

  private FavoriteDao favoriteDao;

  @Autowired
  public FavoriteServiceImpl(FavoriteDao favoriteDao) {
    this.favoriteDao = favoriteDao;
  }

  @Override
  public Optional<Favorite> getFavorite(Long id) {
    return favoriteDao.findById(id);
  }

  @Override
  @Transactional
  public void addFavorite(Favorite favorite) {
    favoriteDao.save(favorite);
  }

  @Override
  @Transactional
  public void deleteFavorite(Long id) {
    favoriteDao.deleteById(id);
  }
}