#!/bin/sh

ts=$(date +"%d%m%Y:%H%M%S")
workdir=$(pwd)
metricsdir=${workdir}/metrics-${ts}

docker run --rm -it -v ${workdir}:/config -v ${metricsdir}:/metrics ghcr.io/sotudeko/mgen:@APPVER@


