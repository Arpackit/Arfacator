for file in $(ls $1); do
  git add $1$file && git commit -m "add $file"
done
