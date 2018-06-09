#!/usr/bin/env Rscript
args = commandArgs(trailingOnly=TRUE)
path = args[1]
print(path)
#path="Foreign_relations_of_North_Korea.csv"

plotEntity = function(data, entity){
  #sentiment plot
  plot(data$timestamp, data$sentiment, type="l", ylim=c(-1,1), xlab="Time", ylab="sentiment", main=paste("Sentiment plot", path, entity))
  
  #emotions plot
  emos = c("joy", "anger", "disgust", "sadness", "fear")
  plot(data$timestamp, rep(0, nrow(data)), type="l", ylim=c(0,1), xlab="Time", ylab="emotions", main=paste("Emotions plot", path, entity))
  cols = rainbow(length(emos))
 # legend("bottomleft", )
  i = 1
  for(emo in emos){
    lines(data$timestamp, data[,emo], col=cols[i])
    i=i+1
  }
}


#read in .csv
data <- read.csv(file=path, header=TRUE, sep=",")
data$timestamp = as.Date(data$timestamp)

pdf("output.pdf")
for(entity in unique(data$entity)){
  plotEntity(data[data$entity==entity,], entity)
}
dev.off()

#do analysis


#Output stats