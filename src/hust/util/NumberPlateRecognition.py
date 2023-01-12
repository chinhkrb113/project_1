import cv2
import os
import sys
from google.cloud import vision

def detect_text(path):
    """Detects text in the file."""
    from google.cloud import vision
    import io
    client = vision.ImageAnnotatorClient()

    with io.open(path, 'rb') as image_file:
        content = image_file.read()

    image = vision.Image(content=content)

    response = client.text_detection(image=image)
    texts = response.text_annotations
    print('Plate:')
    plate = ''
    for text in texts[1:]:
        plate += text.description
        
        # vertices = (['({},{})'.format(vertex.x, vertex.y)
        #             for vertex in text.bounding_poly.vertices])
        #
        # print('bounds: {}'.format(','.join(vertices)))
    print(plate)
    if response.error.message:
        raise Exception(
            '{}\nFor more info on error messages, check: '
            'https://cloud.google.com/apis/design/errors'.format(
                response.error.message))
        
def localize_objects(path):
    """Localize objects in the local image.

    Args:
    path: The path to the local file.
    """
    client = vision.ImageAnnotatorClient()

    with open(path, 'rb') as image_file:
        content = image_file.read()
    
    img = cv2.imread(path)
    h = img.shape[0]
    w = img.shape[1]

    image = vision.Image(content=content)

    objects = client.object_localization(
        image=image).localized_object_annotations
    
    crop_path_full = ''
    #print('Number of objects found: {}'.format(len(objects)))
    for object_ in objects:
        #print('\n{} (confidence: {})'.format(object_.name, object_.score))
        # print('Normalized bounding polygon vertices: ')
        if object_.name == 'License plate':
            
            vertexs = object_.bounding_poly.normalized_vertices
            #for vertex in vertexs:
                #print(' - ({}, {})'.format(vertex.x, vertex.y))
            y1 = vertexs[0].y * h
            y2 = vertexs[2].y * h
            x1 = vertexs[0].x * w
            x2 = vertexs[2].x * w

            y1, y2, x1, x2 = int(y1), int(y2), int(x1), int(x2)
            crop_img = img[y1:y2, x1:x2]
            crop_path_name = "crop_" + path.split('/')[-1]
            
            for i in path.split('/')[:-1]:
                crop_path_full = crop_path_full + i + '/'
            crop_path_full = crop_path_full + crop_path_name
            # print("crop is " + crop_path_full)
            # print("path=" + path)
            
            cv2.imwrite('{}'.format(crop_path_full), crop_img)
    
    return crop_path_full
        

#curPath = os.path.dirname(__file__)
#print(curPath)
#print(fileDir)
#GT/img_history/*
# path = os.path.relpath("..\\..\\..\\img_history\\0014927589_2021-06-15_15'28'25.jpg", curPath)
path_folder = "../../../img_history/"
imgPath = sys.argv[1]
#print(imgPath)
#path_folder = "..\\..\\..\\img_history\\"
# print(os.listdir(path))
# print(path)

path_img = os.path.join(path_folder, imgPath)
crop_path_full = localize_objects(path_img)
try:
    detect_text("{}".format(crop_path_full))
except:
    print("don't have file crop")
    detect_text(path_img)