from tareas1a10 import *

def tarea11(files, month):
    for file in files:
        print(file)
        tags = tarea4(file)
        if tags == null: continue

        tweetsMes = 0

        # Imitación de tarea6
        f = open(file).read().split("\n")[1:]
        tagsMes = []
        for tag in tags:
            tagsMes.append(Tag(tag, 0))
        for linea in f:
            fecha = linea.split(";")[0]
            mes = int(fecha.split("-")[1])
            if mes != month: continue #solo puede ser en el mes adecuado
            tweetsMes += 1
            texto = linea.split(";")[3]
            tagsLinea = t4extraccionTagLinea(texto)
            if (tagsLinea == null): continue
            # Por cada tag en la línea...
            for tag in tagsLinea:
                # ...comprueba qué tag es y añade una ocurrencia
                for otag in tagsMes:
                    if (tag.equalsIgnoreCase(otag.getTexto())):
                        otag.sumarOcurrencias(1)
                        break # Se usa aquí un break para mantener la simplicidad del for
                              # y evitar realizar un número excesivo de iteraciones