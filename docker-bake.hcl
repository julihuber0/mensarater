group "default" {
  targets = ["backend"]
}

target "backend" {
  context = "./backend"
    dockerfile = "Dockerfile"
    tags = [
      "itzthedockerjules/mensarater:backend-${VERSION}",
      "itzthedockerjules/mensarater:backend-latest",
    ]
    platforms = [
      "linux/amd64",
      "linux/arm64"
    ]
}

variable "VERSION" {
  type    = string
  default = "v1.0.0"
}
