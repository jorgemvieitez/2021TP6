class Tag:
    name = "#hi"
    amt = 0
    def __init__(self, amt):
        self.amt = amt

def tagRepr(tag: Tag) -> str:
    # Representar la cantidad de ocurrencias de un tag en forma de asteriscos
    out = "*" * (tag.amt // 5)
    if tag.amt % 5 != 0: out += "*"
    return out

def histogramaTag(files: list[str], tag: str) -> None:
    # Leer los archivos y separar ocurrencias de tags por meses
    for file in files:
        open_as_scanner(file)
        file.nextLine() # cabecera
        ocurrencias = [0] * 12
        for linea in file.asLines():
            fecha = linea.split(";")[0]
            mes = int(fecha.split("-")[1])
            tweet = linea.split(";")[3]
            tags = extraccionTagLinea(linea)
            ocurrencias[mes-1] += contarTag(tags, tag)
        
    
    MESES = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]
    print(f"Tag: {tag.name}")
    for mes in range(12):
        print(f"{MESES[mes]} ({tags[mes].amt}): {tagRepr(tags[mes])}")´


print(tagRepr(Tag(0)))