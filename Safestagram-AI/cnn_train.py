# -*- coding: utf-8 -*-
"""

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1jCD7sISJH99fdVqduhTWX04S7BOqlZMQ
"""

from google.colab import drive
drive.mount('/content/drive')

import tensorflow as tf
import numpy as np
from tensorflow import keras
import os
import cv2
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.preprocessing import image
import matplotlib.pyplot as plt

train = ImageDataGenerator(rescale=1/255)
test = ImageDataGenerator(rescale=1/255)

train_dataset = train.flow_from_directory("/content/drive/MyDrive/dataset/train/",
                                          target_size=(150,150),
                                          batch_size = 32,
                                          class_mode = 'binary')
                                         
test_dataset = test.flow_from_directory("/content/drive/MyDrive/dataset/validation/",
                                          target_size=(150,150),
                                          batch_size =32,
                                          class_mode = 'binary')

test_dataset.class_indices

model = keras.Sequential()
model.add(keras.layers.Conv2D(32,(3,3),activation='relu',input_shape=(150,150,3)))
model.add(keras.layers.MaxPool2D(2,2))
model.add(keras.layers.Conv2D(64,(3,3),activation='relu'))
model.add(keras.layers.MaxPool2D(2,2))
model.add(keras.layers.Conv2D(128,(3,3),activation='relu'))
model.add(keras.layers.MaxPool2D(2,2))
model.add(keras.layers.Conv2D(128,(3,3),activation='relu'))
model.add(keras.layers.MaxPool2D(2,2))

model.add(keras.layers.Flatten())
model.add(keras.layers.Dense(512,activation='relu'))
model.add(keras.layers.Dense(1,activation='sigmoid'))
model.compile(optimizer='adam',loss='binary_crossentropy',metrics=['accuracy'])

callbacks = [
             tf.keras.callbacks.ModelCheckpoint(
                 "/content/drive/MyDrive/modelsV2/model_{epoch:02d}.h5", 
                 monitor='val_loss', 
                 verbose=1, save_best_only=True,
              )
]

model.fit(train_dataset,
         steps_per_epoch = 125,
         epochs = 20,
         validation_data = test_dataset ,
         callbacks = callbacks 
         )

