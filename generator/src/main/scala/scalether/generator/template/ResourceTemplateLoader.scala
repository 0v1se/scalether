package scalether.generator.template

import java.io.{InputStream, InputStreamReader}

import freemarker.cache.TemplateLoader

class ResourceTemplateLoader(basePath: String) extends TemplateLoader {

  override def getLastModified(templateSource: scala.Any) = 0

  override def getReader(templateSource: scala.Any, encoding: String) =
    new InputStreamReader(templateSource.asInstanceOf[InputStream], encoding)

  override def closeTemplateSource(templateSource: scala.Any): Unit = {
    templateSource.asInstanceOf[InputStream].close()
  }

  override def findTemplateSource(name: String): InputStream =
    getClass.getClassLoader.getResourceAsStream(basePath + "/" + name + ".ftl")
}
