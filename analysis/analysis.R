#!/usr/bin/env Rscript
args = commandArgs(trailingOnly=TRUE)
path = args[1]
#print(path)
#path="Donald_Trump.csv"
path = "North_Korea.csv"
#path = "Cow_tipping.csv"
#path ="Dismissal_of_James_Comey.csv"
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


plotEntity2 = function(data){
  cols=rainbow(5)
  pdf("test.pdf")
  boxplot(ylim =c(-1,1),data$sentiment, main="Sentiment");
  boxplot(ylim = c(0,1), data$joy, main="Joy", col=cols[1])
  boxplot(ylim = c(0,1),data$anger, main="Anger", col=cols[2]);
  boxplot(ylim = c(0,1),data$disgust, main="Disgust", col=cols[3]);
  boxplot(ylim = c(0,1),data$sadness, main="Sadness", col=cols[4]);
  boxplot(ylim = c(0,1),data$fear, main="Fear", col=cols[5]);
  dev.off()
}


#read in .csv
data <- read.csv(file=path, header=TRUE, sep=",")
data$timestamp = as.Date(data$timestamp)

#pdf("output.pdf")
for(entity in unique(data$entity)){
  print(data[data$entity==entity,])
  plotEntity(data[data$entity==entity,], entity)
}
#dev.off()

#do analysis


#Output stats