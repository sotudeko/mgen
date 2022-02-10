#!/bin/sh

ts=$(date +"%d%m%Y:%H%M%S")
workdir=$(pwd)
metricsdir=${workdir}/iqmetrics-${ts}

docker run --rm -it -v ${workdir}:/config -v ${metricsdir}:/iqmetrics ghcr.io/sotudeko/mgen:@APPVER@


