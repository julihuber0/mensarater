target "backend" {
  context = "./backend"
    dockerfile = "Dockerfile"
    tags = [
      "itzthedockerjules/mensarater:backend-${RELEASE_TAG}",
      "itzthedockerjules/mensarater:backend-latest",
    ]
    platforms = [
      "linux/amd64",
      "linux/arm64"
    ]
}

target "frontend" {
  context = "./ui"
    dockerfile = "Dockerfile"
    tags = [
      "itzthedockerjules/mensarater:frontend-${RELEASE_TAG}",
      "itzthedockerjules/mensarater:frontend-latest",
    ]
    platforms = [
      "linux/amd64",
      "linux/arm64"
    ]
}

variable "RELEASE_TAG" {
  type    = string
  default = "v1.0.0"
}
