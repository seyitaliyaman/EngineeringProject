from flask import Flask,request
import tensorflow as tf
import numpy as np
from tensorflow import keras
import os
import cv2
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.preprocessing import image
import matplotlib.pyplot as plt

app = Flask(__name__)


@app.route('/classify', methods=['POST'])
def classifyImage():

    path = request.form.get("postPath", "")
    result = predictImage(path)
    return result;


def predictImage(filename):

    model = tf.keras.models.load_model("model_03.h5")
    img1 = image.load_img(filename, target_size=(150, 150))
    Y = image.img_to_array(img1)
    X = np.expand_dims(Y, axis=0)
    val = model.predict(X)
    print(val)
    if val == 1:
        return "Violence"
    elif val == 0:
        return "Normal"

if __name__ == '__main__':
    app.run()