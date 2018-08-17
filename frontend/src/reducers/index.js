import {combineReducers} from 'redux';
import feedReducer from './feedReducer'
import userPageReducer from "./userPageReducer";
import chatsReducer from "./chatsReducer";
import singleChatReducer from "./singleChatReducer";
import followedReducer from "./followedReducer";
import followingReducer from "./followingReducer";
import likedReducer from "./likedReducer";
import userPicturesReducer from "./userPicturesReducer";
import searchUsersReducer from "./searchUsersReducer";
import searchPostsReducer from "./searchPostsReducer";
import currentUserReducer from "./currentUserReducer";

export default combineReducers({
  feed: feedReducer,
  userPosts: userPageReducer,
  chats: chatsReducer,
  chat: singleChatReducer,
  followed: followedReducer,
  following: followingReducer,
  likedPosts: likedReducer,
  user: userPicturesReducer,
  foundUsers: searchUsersReducer,
  foundPosts: searchPostsReducer,
  currentUser: currentUserReducer
})