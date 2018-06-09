#!/usr/bin/env Rscript
args = commandArgs(trailingOnly=TRUE)
path = args[1]
print(path)
#path="example.csv"
#library()

plotEntity = function(data, entity){
  #sentiment plot
  plot(NA, ylim=c(-1,1), xlim=c(1, nrow(data)), xlab="Time", ylab="sentiment", main=paste("Sentiment plot", path, entity))
  lines(data$timestamp, data$sentiment)
  
  #emotions plot
  emos = c("joy", "anger", "disgust", "sadness", "fear")
  plot(NA, ylim=c(0,1), xlim=c(1, nrow(data)), xlab="Time", ylab="emotions", main=paste("Emotions plot", path, entity))
  cols = rainbow(length(emos))
  i = 1
  for(emo in emos){
    print(dim(data[emo]))
    print(dim(data$timestamp))
    lines(data$timestamp, data[emo])#, col=cols[i])
    i=i+1
  }
}


#read in .csv
data <- read.csv(file=path, header=TRUE, sep=",")

for(entity in unique(data$entity)){
  print(entity)
  print(data[data$entity==entity,])
  plotEntity(data[data$entity==entity,], entity)
}



#do analysis


#Output stats