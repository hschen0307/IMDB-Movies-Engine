Create Table MOVIES
(
ID VARCHAR(10),
TITLE VARCHAR(120),
IMDBID VARCHAR(10),
SPANISHTITLE VARCHAR(110),
IMDBPICTUREURL VARCHAR(150),
YEAR NUMBER,
RTID VARCHAR(100),
RTALLCRITICSRATING NUMBER,
RTALLCRITICSNUMREVIEWS NUMBER,
RTALLCRITICSNUMFRESH NUMBER,
RTALLCRITICSNUMROTTEN NUMBER,
RTALLCRITICSSCORE NUMBER,
RTTOPCRITICSRATING NUMBER,
RTTOPCRITICSNUMREVIEWS NUMBER,
RTTOPCRITICSNUMFRESH NUMBER,
RTTOPCRITICSNUMROTTEN NUMBER,
RTTOPCRITICSSCORE NUMBER,
RTTOPRTAUDIENCERATING NUMBER,
RTAUDIENCENUMRATINGS NUMBER,
RTAUDIENCESCORE NUMBER,
RTPICTUREURL VARCHAR(80),
Primary key (ID)
);



Create Table GENRES
(
MOVIEID VARCHAR(10),
GENRE VARCHAR(15),
Primary key (MOVIEID, GENRE),
foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE
);



Create Table Countries
(
MOVIEID VARCHAR(10),
country VARCHAR(30),
Primary key (MOVIEID),
foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE
);



Create Table ACTORS
(
  MOVIEID VARCHAR(10),
  actorID VARCHAR(50),
  actorName VARCHAR(50),
  Primary key (MOVIEID, actorID),
  foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE
);



Create Table Directors
(
  MOVIEID VARCHAR(10),
  directorID VARCHAR(50),
  directorName VARCHAR(50),
  Primary key (MOVIEID, directorID),
  foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE
);



Create Table Tags
(
  id VARCHAR(10),
  tagName VARCHAR(50),
  Primary key (id)
);



Create Table Movie_tags
(
  MOVIEID VARCHAR(10),
  tagID VARCHAR(10),
  
  Primary key (MOVIEID, tagID),
  foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE,
  foreign key (tagID) REFERENCES TAGS (ID) ON DELETE CASCADE
);



Create Table user_ratings
(
  userID VARCHAR(10),
  movieID VARCHAR(10),
  rating NUMBER,
  datetime DATE,
  --Primary key(datetime)
  Primary key (userID, movieID),
  foreign key (MOVIEID) REFERENCES MOVIES(ID) ON DELETE CASCADE
);



Create Index In_YEAR ON MOVIES(year);
Create Index In_ratings1 ON MOVIES(RTALLCRITICSRATING);
Create Index In_ratings2 ON MOVIES(RTTOPCRITICSRATING); 
Create Index In_ratings3 ON MOVIES(RTTOPRTAUDIENCERATING); 
Create Index In_numReviews1 ON MOVIES(RTTOPCRITICSNUMREVIEWS);
Create Index In_numReviews2 ON MOVIES(RTALLCRITICSNUMREVIEWS);
Create Index In_numReviews3 ON MOVIES(RTAUDIENCENUMRATINGS);
Create Index In_GENRE ON GENRES(GENRE);
Create Index In_country ON Countries(country);
Create Index In_actorName ON ACTORS(actorName);
Create Index In_directorName ON Directors(directorName);
Create Index In_rating ON user_ratings(rating);
Create Index In_datetime ON user_ratings(datetime);
Create Index In_userID ON user_ratings(userID);
Create Index In_userMID ON user_ratings(movieid);
Create Index In_actorMID ON ACTORS(movieid);
create Index In_directorMID ON directors(movieid);
create Index In_tagMID ON Movie_Tags(movieid);
Create Index In_genreMID ON genres(movieid);



